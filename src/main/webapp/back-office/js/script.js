// Fonctions utilitaires pour le back-office
document.addEventListener('DOMContentLoaded', function () {
    initFormValidation();
    initAlertDismissal();

    // Confirmation des suppressions
    const deleteButtons = document.querySelectorAll('[data-confirm]');
    Array.from(deleteButtons).forEach(button => {
        event.stopPropagation();
    }
            form.classList.add('was-validated');
}, false);
    });

// Confirmation des suppressions
const deleteButtons = document.querySelectorAll('[data-confirm]');
Array.from(deleteButtons).forEach(button => {
    button.addEventListener('click', event => {
        if (!confirm(button.dataset.confirm || 'Êtes-vous sûr de vouloir supprimer cet élément ?')) {
            event.preventDefault();
        }
    });
});

// Mise à jour dynamique des villes d'arrivée en fonction de la ville de départ
const villeDepartSelect = document.querySelector('select[name="villeDepartId"]');
const villeArriveSelect = document.querySelector('select[name="villeArriveId"]');

if (villeDepartSelect && villeArriveSelect) {
    villeDepartSelect.addEventListener('change', function () {
        if (this.value) {
            fetch(`/admin/api/villes/arrivee?villeDepartId=${this.value}`)
                .then(response => response.json())
                .then(villes => {
                    villeArriveSelect.innerHTML = '<option value="">Sélectionnez une ville</option>';
                    villes.forEach(ville => {
                        const option = new Option(ville.nom, ville.id);
                        villeArriveSelect.add(option);
                    });
                });
        }
    });
}

// Formatage des nombres en prix
const priceInputs = document.querySelectorAll('.price-input');
Array.from(priceInputs).forEach(input => {
    input.addEventListener('input', function () {
        let value = this.value.replace(/[^\d]/g, '');
        if (value) {
            value = parseInt(value, 10);
            this.value = new Intl.NumberFormat('fr-FR', {
                style: 'currency',
                currency: 'MGA'
            }).format(value);
        }
    });
});

// Gestionnaire de filtres avancés
const toggleFilters = document.querySelector('.toggle-filters');
const filterForm = document.querySelector('.filter-form');

if (toggleFilters && filterForm) {
    toggleFilters.addEventListener('click', () => {
        filterForm.classList.toggle('d-none');
    });
}

// Validation des dates de vol
const dateDepart = document.querySelector('input[name="dateDepart"]');
if (dateDepart) {
    dateDepart.addEventListener('change', function () {
        const now = new Date();
        const selectedDate = new Date(this.value);
        if (selectedDate < now) {
            alert('La date de départ doit être ultérieure à la date actuelle');
            this.value = '';
        }
    });
}

// Validation du pourcentage de réduction
const reductionInput = document.querySelector('input[name="reduction"]');
if (reductionInput) {
    reductionInput.addEventListener('input', function () {
        const value = parseFloat(this.value);
        if (value < 0) this.value = 0;
        if (value > 100) this.value = 100;
    });
}
});

// Fonction pour formater les dates
function formatDate(date) {
    return new Intl.DateTimeFormat('fr-FR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    }).format(new Date(date));
}

// Fonction pour valider les paramètres système
function validateSystemParams() {
    const heuresReservation = document.querySelector('input[name="heuresMinimumReservation"]');
    const heuresAnnulation = document.querySelector('input[name="heuresMinimumAnnulation"]');

    if (parseInt(heuresAnnulation.value) <= parseInt(heuresReservation.value)) {
        alert('Le délai d\'annulation doit être supérieur au délai de réservation');
        return false;
    }
    return true;
}