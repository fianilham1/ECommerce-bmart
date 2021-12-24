import Axios from "axios";
import { CART_ADD_ITEM, CART_REMOVE_ITEM, CART_GET_ITEM, 
  CART_ADD_ITEM_FAIL, CART_REMOVE_ITEM_FAIL, CART_GET_ITEM_FAIL,
  EMPTY_CART_ITEM } from "../constants/cartConstants";
import { BASE_URL } from "../constants/api";
import Cookie from 'js-cookie';
import { ShowSessionOut } from "../components/SessionAlert";

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
       if(data.code!==200) throw {message:data.errors.error[0]};
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
       if(data.code!==200) throw {message:data.errors.error[0]};
      const cart = data.data; 
      dispatch({
        type: CART_ADD_ITEM, payload: cart
      });
    }
   

  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
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
     if(data.code!==200) throw {message:data.errors.error[0]};
    const deleteMessage = data.data.deleteMessage;
    dispatch({ 
      type: CART_REMOVE_ITEM, payload: deleteMessage 
    });

  }catch (error){
    if(error.response.status===401) {
      ShowSessionOut()
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
     if(data.code!==200) throw {message:data.errors.error[0]};
    const cart = data.data;
    console.log("[GET] cart list :",cart)
    dispatch({ 
      type: CART_GET_ITEM, payload: cart 
    });

  }catch (error){
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: CART_GET_ITEM_FAIL, payload: error.message });
  }
  
}

const emptyCartAfterSignOut = () => async (dispatch, getState) => {
  dispatch({ 
    type: EMPTY_CART_ITEM, payload: {} 
  });
}

export { addToCart, removeFromCart, getCart, emptyCartAfterSignOut }