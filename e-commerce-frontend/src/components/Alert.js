import Swal from 'sweetalert2';

export function ShowRegisterSucces() {
  return Swal.fire({
    icon: 'error',
    title: 'Yee, register is success',
    showConfirmButton: false,
    timer: 1500
  })
}

export function ShowUpdateSucces() {
  return Swal.fire({
    icon: 'error',
    title: 'Yee, update is success',
    showConfirmButton: false,
    timer: 1500
  })
}

export function ShowAddProductToCartSuccess(){
  return Swal.fire({
    icon: 'success',
    title: 'The Product has been added to Your Cart',
    showConfirmButton: false,
    timer: 1500
  })
}

export function ShowAddProductReviewSuccess(){
  return Swal.fire({
    icon: 'success',
    title: 'Add Review is success',
    showConfirmButton: false,
    timer: 1500
  })
}
