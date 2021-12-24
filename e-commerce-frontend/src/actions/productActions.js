import {
  PRODUCT_LIST_REQUEST,
  PRODUCT_LIST_SUCCESS,
  PRODUCT_LIST_FAIL,
  PRODUCT_DETAILS_REQUEST,
  PRODUCT_DETAILS_SUCCESS,
  PRODUCT_DETAILS_FAIL,
  PRODUCT_SAVE_REQUEST,
  PRODUCT_SAVE_SUCCESS,
  PRODUCT_SAVE_FAIL,
  PRODUCT_DELETE_SUCCESS,
  PRODUCT_DELETE_FAIL,
  PRODUCT_DELETE_REQUEST,
  PRODUCT_REVIEW_SAVE_REQUEST,
  PRODUCT_REVIEW_SAVE_FAIL,
  PRODUCT_REVIEW_SAVE_SUCCESS,
} from '../constants/productConstants';
import Axios from 'axios';
import { BASE_URL } from "../constants/api";
import Cookie from 'js-cookie';
import { ShowSessionOut } from "../components/SessionAlert";

const listProducts = (
  category = '',
  searchKeyword = '',
  sortOrder = ''
) => async (dispatch) => {
  try {
    dispatch({ type: PRODUCT_LIST_REQUEST });
    const sortArray = sortOrder.split("/");
    const sort_by = sortArray.length>=1 ? sortArray[0] : sortOrder;
    const direction = sortArray.length>=1 ? sortArray[1] : '';
    
    const { data } = await Axios.get(
      BASE_URL+'/products?category=' +
        category +
        '&searchKeyword=' +
        searchKeyword +
        '&sort_by=' +
        sort_by +
        '&direction=' +
        direction
    );
     if(data.code!==200) throw {message:data.errors.error[0]};
    const productList = data.data.content;
    console.log("[GET] data product list : ",productList)
    setTimeout(() => {
      dispatch({ type: PRODUCT_LIST_SUCCESS, payload: productList });
    }, 500);
  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: PRODUCT_LIST_FAIL, payload: error.message });
  }
};

const saveProduct = (product) => async (dispatch, getState) => {
  try {
    console.log("updated product : ",product)
    dispatch({ type: PRODUCT_SAVE_REQUEST, payload: product });
    const {
      userSignin: { userInfo },
    } = getState();
    if (!product.id) {
      const { data } = await Axios.post(BASE_URL+'/products', { productList:[
        product
      ]}, 
      {
        headers: {
          Authorization: 'Bearer ' + userInfo.token,
        },
      });
       if(data.code!==200) throw {message:data.errors.error[0]};
      const newProduct = data.data;
      dispatch({ type: PRODUCT_SAVE_SUCCESS, payload: newProduct });
    } else {
      const { data } = await Axios.put(
        BASE_URL+'/products', product,
        {
          headers: {
            Authorization: 'Bearer ' + userInfo.token,
          },
        }
      );
       if(data.code!==200) throw {message:data.errors.error[0]};
      const updatedProduct = data.data;
      product.name = updatedProduct.name;
      dispatch({ type: PRODUCT_SAVE_SUCCESS, payload: updatedProduct });
    }
  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: PRODUCT_SAVE_FAIL, payload: error.message });
  }
};

const detailsProduct = (productId) => async (dispatch) => {
  try {
    dispatch({ type: PRODUCT_DETAILS_REQUEST, payload: productId });
    const { data } = await Axios.get(BASE_URL+'/products/' + productId);
     if(data.code!==200) throw {message:data.errors.error[0]};
    const detailsProduct = data.data;
    setTimeout(function() {
      dispatch({ type: PRODUCT_DETAILS_SUCCESS, payload: detailsProduct });
    }, 500);
  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: PRODUCT_DETAILS_FAIL, payload: error.message });
  }
};

const deleteProdcut = (productId) => async (dispatch, getState) => {
  try {
    const {
      userSignin: { userInfo },
    } = getState();
    dispatch({ type: PRODUCT_DELETE_REQUEST, payload: productId });
    const { data } = await Axios.delete(BASE_URL+'/products/' + productId, {
      headers: {
        Authorization: 'Bearer ' + userInfo.token,
      },
    });
     if(data.code!==200) throw {message:data.errors.error[0]};
    const deleteMessage = data.data.deleteMessage;
    dispatch({ type: PRODUCT_DELETE_SUCCESS, payload: deleteMessage, success: true });
  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: PRODUCT_DELETE_FAIL, payload: error.message });
  }
};

const saveProductReview = (review) => async (dispatch, getState) => {
  try {
    const {
      userSignin: {
        userInfo: { token },
      },
    } = getState();
    dispatch({ type: PRODUCT_REVIEW_SAVE_REQUEST, payload: review });
    const { data } = await Axios.post(
      BASE_URL+'/products/review',
      review,
      {
        headers: {
          Authorization: 'Bearer ' + token,
        },
      }
    );
     if(data.code!==200) throw {message:data.errors.error[0]};
    const productReview = data.data;
    review.comment = productReview.comment;
    dispatch({ type: PRODUCT_REVIEW_SAVE_SUCCESS, payload: productReview });
  } catch (error) {
    // report error
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo")
    };
    dispatch({ type: PRODUCT_REVIEW_SAVE_FAIL, payload: error.message });
  }
};

export {
  listProducts,
  detailsProduct,
  saveProduct,
  deleteProdcut,
  saveProductReview,
};
