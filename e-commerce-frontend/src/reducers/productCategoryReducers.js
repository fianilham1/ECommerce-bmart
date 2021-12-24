import {
  PRODUCT_CATEGORY_LIST_REQUEST,
  PRODUCT_CATEGORY_LIST_SUCCESS,
  PRODUCT_CATEGORY_LIST_FAIL
} from '../constants/productCategoryConstants';

function productCategoryListReducer(state = { productCategoryList: [] }, action) {
  switch (action.type) {
    case PRODUCT_CATEGORY_LIST_REQUEST:
      return { loading: true, productCategoryList: [] };
    case PRODUCT_CATEGORY_LIST_SUCCESS:
      return { loading: false, productCategoryList: action.payload };
    case PRODUCT_CATEGORY_LIST_FAIL:
      return { loading: false, error: action.payload };
    default:
      return state;
  }
}


export {
  productCategoryListReducer
};
