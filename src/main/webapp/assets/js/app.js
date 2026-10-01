document.addEventListener('DOMContentLoaded', () => {
 document.querySelectorAll('img[data-fallback]').forEach(img => { const fallback=()=>{img.onerror=null;img.src=img.dataset.fallback;};img.onerror=fallback;if(img.complete && img.naturalWidth===0)fallback(); });
 document.querySelectorAll('form[data-confirm]').forEach(form => form.addEventListener('submit',event=>{if(!confirm(form.dataset.confirm))event.preventDefault();}));
});
