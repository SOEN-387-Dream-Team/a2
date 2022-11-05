/**
 * This delays the login form submission to showcase an "attempting to login" toast
 */

function showToast() {

    bootstrap.Toast.Default.delay = 1000;
    const toastLiveExample = document.getElementById('loginAttemptToast')

    const myForm = document.getElementById('loginForm');
    myForm.addEventListener('submit', handleSubmit);
    var submitTimer;

    function handleSubmit(event) {
        console.log('attempt to login Toast created');
        const toast = new bootstrap.Toast(toastLiveExample)
        toast.show()

        console.log('submitTimer set');
        event.preventDefault();



        submitTimer = setTimeout(() => {

            this.submit();

            console.log('Submitted after 5 seconds');
        }, 1000)
    };

}







