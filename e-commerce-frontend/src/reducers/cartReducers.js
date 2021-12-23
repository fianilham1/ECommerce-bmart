import { CART_ADD_ITEM, CART_REMOVE_ITEM, CART_GET_ITEM,
  CART_ADD_ITEM_FAIL, CART_REMOVE_ITEM_FAIL, CART_GET_ITEM_FAIL } from "../constants/cartConstants";

function cartReducer(state = { cartItemsList: [], totalProducts: 0, totalProductsPrice: 0 }, action) {
  switch (action.type) {
    case CART_ADD_ITEM:
      return { cartItemsList: action.payload.cartItemsList, 
        totalProducts: action.payload.totalProducts, 
        totalProductsPrice: action.payload.totalProductsPrice };
    case CART_REMOVE_ITEM:
      return { cartItemsList: state.cartItemsList, 
        totalProducts: state.totalProducts, 
        totalProductsPrice: state.totalProductsPrice };
    case CART_GET_ITEM:
      return { cartItemsList: action.payload.cartItemsList, 
        totalProducts: action.payload.totalProducts, 
        totalProductsPrice: action.payload.totalProductsPrice };
    case CART_ADD_ITEM_FAIL:
      return { error: action.payload };
    case CART_REMOVE_ITEM_FAIL:
      return { error: action.payload };
    case CART_GET_ITEM_FAIL:
      return { error: action.payload };
    default:
      return state
  }
}

export { cartReducer }