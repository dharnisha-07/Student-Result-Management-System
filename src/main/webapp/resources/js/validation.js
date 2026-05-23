function validateStudentForm(event) {
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const phone = document.getElementById('phone').value.trim();

    if (!name) {
        alert('Name is required.');
        event.preventDefault();
        return false;
    }

    if (!email) {
        alert('Email is required.');
        event.preventDefault();
        return false;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Please enter a valid email address.');
        event.preventDefault();
        return false;
    }

    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phone.replace(/[\s\-]/g, ''))) {
        alert('Phone number must be 10 digits.');
        event.preventDefault();
        return false;
    }

    return true;
}

function validateMarksForm(event) {
    const marksObtained = document.getElementById('marksObtained').value;
    const subjectId = document.getElementById('subjectId').value;

    if (!subjectId) {
        alert('Please select a subject.');
        event.preventDefault();
        return false;
    }

    if (!marksObtained || isNaN(marksObtained) || marksObtained < 0) {
        alert('Marks obtained must be a non-negative number.');
        event.preventDefault();
        return false;
    }

    return true;
}

function validateChangePasswordForm(event) {
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword.length < 6) {
        alert('New password must be at least 6 characters long.');
        event.preventDefault();
        return false;
    }

    if (newPassword !== confirmPassword) {
        alert('New password and confirm password do not match.');
        event.preventDefault();
        return false;
    }

    return true;
}
