import Axios from "axios";
import { CART_ADD_ITEM, CART_REMOVE_ITEM, CART_GET_ITEM, 
  CART_ADD_ITEM_FAIL, CART_REMOVE_ITEM_FAIL, CART_GET_ITEM_FAIL } from "../constants/cartConstants";
import { BASE_URL } from "../constants/api";
import Cookie from 'js-cookie';
import Swal from 'sweetalert2';

const addToCart = (productId, qty, options) => async (dispatch, getState) => {
  const { userSignin: { userInfo } } = getState();
  try {
    if(options==='addQty'){
      const { data } = await Axios.post(BASE_URL+"/cart",{
        product:{
          id:productId
          },
        productQuantity:qty
        },
        {
        headers: {
          Authorization: 'Bearer ' + userInfo.token
        }
      });
      const cart = data.data; 
      dispatch({
        type: CART_ADD_ITEM, payload: cart
      });
    }else{
      const { data } = await Axios.put(BASE_URL+"/cart",{
        product:{
          id:productId
          },
        productQuantity:qty
        },
        {
        headers: {
          Authorization: 'Bearer ' + userInfo.token
        }
      });
      const cart = data.data; 
      dispatch({
        type: CART_ADD_ITEM, payload: cart
      });
    }
   

  } catch (error) {
    if(error.response.status===401) {
      Swal.fire({
        icon: 'error',
        title: 'session over, please sign in',
        showConfirmButton: false,
        timer: 1500
      })
      Cookie.remove("userInfo")
    };
    dispatch({ type: CART_ADD_ITEM_FAIL, payload: error.message });
  }
}
const removeFromCart = (productId) => async (dispatch, getState) => {
  const { userSignin: { userInfo } } = getState();
  try{
    const { data } = await Axios.delete(BASE_URL+"/cart/"+productId,
    {
      headers: {
        Authorization: 'Bearer ' + userInfo.token
      }
    });
    dispatch({ 
      type: CART_REMOVE_ITEM, payload: data 
    });

  }catch (error){
    if(error.response.status===401) {
      Swal.fire({
        icon: 'error',
        title: 'session over, please sign in',
        showConfirmButton: false,
        timer: 1500
      })
      Cookie.remove("userInfo")
    };
    dispatch({ type: CART_REMOVE_ITEM_FAIL, payload: error.message });
  }
  
}
const getCart = () => async (dispatch, getState) => {
  const { userSignin: { userInfo } } = getState();
  try{
    const { data } = await Axios.get(BASE_URL+"/cart",{
      headers: {
        Authorization: 'Bearer ' + userInfo.token,
      }
    });
    const cart = data.data; 
    console.log("cart :",cart)
    dispatch({ 
      type: CART_GET_ITEM, payload: cart 
    });

  }catch (error){
    if(error.response.status===401) {
      Swal.fire({
        icon: 'error',
        title: 'session over, please sign in',
        showConfirmButton: false,
        timer: 1500
      })
      Cookie.remove("userInfo")
    };
    dispatch({ type: CART_GET_ITEM_FAIL, payload: error.message });
  }
  
}
export { addToCart, removeFromCart, getCart }