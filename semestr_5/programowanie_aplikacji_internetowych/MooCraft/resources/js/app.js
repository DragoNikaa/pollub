import './bootstrap';

import Alpine from 'alpinejs';

window.Alpine = Alpine;

Alpine.start();

initToast();

function initToast() {
    document.addEventListener('DOMContentLoaded', () => {
        const toast = document.getElementById('toast');
        if (!toast) return;
        showToast(toast);
        enableToastDismiss(toast);
    });
}

function showToast(toast) {
    toast.style.opacity = '1';
}

function enableToastDismiss(toast) {
    toast.addEventListener('click', () => {
        toast.style.opacity = '0';
        toast.addEventListener('transitionend', () => toast.remove());
    });
}
