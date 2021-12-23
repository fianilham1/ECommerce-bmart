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
import Swal from 'sweetalert2';

const listProducts = (
  category = '',
  searchKeyword = '',
  sortOrder = ''
) => async (dispatch) => {
  try {
    dispatch({ type: PRODUCT_LIST_REQUEST });
    const { data } = await Axios.get(
      BASE_URL+'/products?category=' +
        category +
        '&searchKeyword=' +
        searchKeyword +
        '&sort_by=' +
        sortOrder
    );
    console.log("data product list : ",data)
    const productList = data.data;
    setTimeout(function() {
      dispatch({ type: PRODUCT_LIST_SUCCESS, payload: productList.content });
    }, 500);
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
    dispatch({ type: PRODUCT_LIST_FAIL, payload: error.message });
  }
};

const saveProduct = (product) => async (dispatch, getState) => {
  try {
    dispatch({ type: PRODUCT_SAVE_REQUEST, payload: product });
    const {
      userSignin: { userInfo },
    } = getState();
    if (!product.id) {
      const { data } = await Axios.post(BASE_URL+'/products', product, {
        headers: {
          Authorization: 'Bearer ' + userInfo.token,
        },
      });
      const newProduct = data.data;
      dispatch({ type: PRODUCT_SAVE_SUCCESS, payload: newProduct });
    } else {
      const { data } = await Axios.put(
        BASE_URL+'/products/',
        product,
        {
          headers: {
            Authorization: 'Bearer ' + userInfo.token,
          },
        }
      );
      const updatedProduct = data.data;
      dispatch({ type: PRODUCT_SAVE_SUCCESS, payload: updatedProduct });
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
    dispatch({ type: PRODUCT_SAVE_FAIL, payload: error.message });
  }
};

const detailsProduct = (productId) => async (dispatch) => {
  try {
    dispatch({ type: PRODUCT_DETAILS_REQUEST, payload: productId });
    const { data } = await Axios.get(BASE_URL+'/products/' + productId);
    const detailsProduct = data.data;
    setTimeout(function() {
      dispatch({ type: PRODUCT_DETAILS_SUCCESS, payload: detailsProduct });
    }, 500);
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
    dispatch({ type: PRODUCT_DELETE_SUCCESS, payload: data, success: true });
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
      BASE_URL+'/products/reviews',
      review,
      {
        headers: {
          Authorization: 'Bearer ' + token,
        },
      }
    );
    const productReview = data.data;
    dispatch({ type: PRODUCT_REVIEW_SAVE_SUCCESS, payload: productReview });
  } catch (error) {
    // report error
    if(error.response.status===401) {
      Swal.fire({
        icon: 'error',
        title: 'session over, please sign in',
        showConfirmButton: false,
        timer: 1500
      })
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
