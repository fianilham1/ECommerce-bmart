import {
  PRODUCT_CATEGORY_LIST_REQUEST,
  PRODUCT_CATEGORY_LIST_SUCCESS,
  PRODUCT_CATEGORY_LIST_FAIL
} from '../constants/productCategoryConstants';
import Axios from 'axios';
import { BASE_URL } from "../constants/api";

const listProductCategory = (
  searchKeyword = '',
  sortOrder = ''
) => async (dispatch) => {
  try {
    dispatch({ type: PRODUCT_CATEGORY_LIST_REQUEST });
    const { data } = await Axios.get(
      BASE_URL+'/product-category?&searchKeyword=' +
        searchKeyword +
        '&sort_by=' +
        sortOrder
    );
    if(data.code!==200) throw {message:data.errors.error[0]};
    const productCategoryList = data.data.content;
    console.log("[GET] product category list : ",productCategoryList)
    setTimeout(function() {
      dispatch({ type: PRODUCT_CATEGORY_LIST_SUCCESS, payload: productCategoryList });
    }, 500);
  } catch (error) {
    dispatch({ type: PRODUCT_CATEGORY_LIST_FAIL, payload: error.message });
  }
};


export {
  listProductCategory
};
