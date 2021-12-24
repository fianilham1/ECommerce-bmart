import Swal from 'sweetalert2';

export function ShowSessionOut() {
  return Swal.fire({
    icon: 'error',
    title: 'session over, please sign in',
    showConfirmButton: false,
    timer: 1500
  })
}