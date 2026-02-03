function(){
      const editBtn = document.getElementById('editBtn');
      const modal = document.getElementById('editModal');
      const cancelEdit = document.getElementById('cancelEdit');
      const editForm = document.getElementById('editForm');
      const fileInput = document.getElementById('fileInput');
      const avatarPreview = document.getElementById('avatarPreview');

      // elements to show
      const displayName = document.getElementById('displayName');
      const firstNameText = document.getElementById('firstNameText');
      const lastNameText = document.getElementById('lastNameText');
      const birthDateText = document.getElementById('birthDateText');
      const cityText = document.getElementById('cityText');
      const aboutText = document.getElementById('aboutText');
      const mainPhoto = document.getElem
                <button class="btn secondary" id="messageBtn">Message</button>entById('mainPhoto');
      const photoBadge = document.getElementById('photoBadge');

      // load profile from server and fill UI
      async function loadProfile(){
        try{
          const res = await fetch('/api/profile', { headers: { 'Accept': 'application/json' }});
          if(!res.ok) return;
          const profile = await res.json();

          // fill UI
          firstNameText.textContent = profile.firstName || '';
          lastNameText.textContent = profile.lastName || '';
          birthDateText.textContent = profile.birthDate || '';
          cityText.textContent = profile.city || '';
          aboutText.textContent = profile.aboutMe || '';
          displayName.textContent = ((profile.firstName || '') + ' ' + (profile.lastName || '')).trim() || 'Your name';
          const age = profile.birthDate ? Math.floor((new Date().getFullYear()) - (new Date(profile.birthDate).getFullYear())) : '';
          photoBadge.textContent = `${profile.firstName || ''}${age? ', ' + age : ''} · ${profile.city || ''}`;
          mainPhoto.src = profile.mainPhotoUrl || 'avatar.jpg';
          avatarPreview.src = mainPhoto.src;

          // form defaults
          document.getElementById('firstName').value = profile.firstName || '';
          document.getElementById('lastName').value = profile.lastName || '';
          document.getElementById('birthDate').value = profile.birthDate || '';
          document.getElementById('city').value = profile.city || '';
          document.getElementById('aboutMe').value = profile.aboutMe || '';

        }catch(err){
          console.warn('Failed to load profile', err);
        }
      }

      // open modal
      editBtn.addEventListener('click', ()=>{
        modal.classList.add('open');
      });

      cancelEdit.addEventListener('click', ()=>{
        modal.classList.remove('open');
        clearErrors();
      });

      // preview local avatar
      fileInput.addEventListener('change', ()=>{
        const f = fileInput.files[0];
        if(!f) return;
        const reader = new FileReader();
        reader.onload = e => avatarPreview.src = e.target.result;
        reader.readAsDataURL(f);
        document.getElementById('error-file').textContent = '';
      });

      function clearErrors(){
        document.querySelectorAll('.error').forEach(e=>{ e.textContent=''; });
      }

      // submit: two-step (profile PUT then optional photo POST)
      editForm.addEventListener('submit', async (ev)=>{
        ev.preventDefault();
        clearErrors();

        const profile = {
          firstName: document.getElementById('firstName').value.trim(),
          lastName: document.getElementById('lastName').value.trim(),
          birthDate: document.getElementById('birthDate').value,
          city: document.getElementById('city').value.trim(),
          aboutMe: document.getElementById('aboutMe').value.trim()
        };

        // basic client-side checks
        if(!profile.firstName){ document.getElementById('error-firstName').textContent='Required'; return; }

        // 1) save profile
        const csrfTokenEl = document.getElementById('csrfToken');
        const headers = { 'Content-Type':'application/json' };
        if(csrfTokenEl) headers['X-CSRF-TOKEN'] = csrfTokenEl.value;

        const res = await fetch('/api/profile', { method:'PUT', headers, body: JSON.stringify(profile) });
        if(!res.ok){
          try{
            const body = await res.json();
            if(body.errors){ Object.entries(body.errors).forEach(([k,v])=>{ const el = document.getElementById('error-'+k); if(el) el.textContent=v; }); }
          }catch(e){ alert('Save failed'); }
          return;
        }

        // 2) upload photo if present
        if(fileInput.files.length>0){
          const formData = new FormData();
          formData.append('file', fileInput.files[0]);
          if(csrfTokenEl) formData.append('_csrf', csrfTokenEl.value);

          const up = await fetch('/api/profile/photo', { method:'POST', body: formData });
          if(!up.ok){
            try{ const b = await up.json(); if(b.error) document.getElementById('error-file').textContent = b.error; else alert('Photo failed'); }
            catch(e){ alert('Photo failed'); }
            return;
          }
        }

        // success
        modal.classList.remove('open');
        await loadProfile();
      });

      // initial load
      loadProfile();
})();