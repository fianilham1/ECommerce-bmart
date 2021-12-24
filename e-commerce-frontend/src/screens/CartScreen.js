import React, { useEffect } from 'react';
import { removeFromCart, addToCart } from '../actions/cartActions';
import { useDispatch, useSelector } from 'react-redux';
import { Link, Redirect } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import Swal from 'sweetalert2';

function CartScreen() {

  const cart = useSelector(state => state.cart);
  const { cartItemsList, totalProducts, totalProductsPrice } = cart;
  const userSignin = useSelector((state) => state.userSignin);
  const { userInfo } = userSignin;

  const dispatch = useDispatch();
  
  const removeFromCartHandler = (productId) => {
    dispatch(removeFromCart(productId));
  }
  useEffect(() => {
    console.log("RERENDER->userinfo",userInfo)
  }, [cartItemsList,userInfo]);

  const checkoutHandler = () => {
    Swal.fire({
      title: 'Are you sure to checkout?',
      text: "you can check it again !",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, checkout!'
    }).then((result) => {
      if (result.isConfirmed) {
      
      }
    })
  }

  if(!userInfo) return <Redirect to="/signin?redirect=" />

  console.log("cartItemsList",cartItemsList)

  return <div className="cart">
    <div className="cart-list">
      <ul className="cart-list-container">
        <li>
          <h3>
            Shopping Cart
          </h3>
          <div>
            Price
          </div>
          <div>
            Action
          </div>
        </li>
        {
          cartItemsList.length===0?
            <div>
              Cart is empty
            </div>
            :
            cartItemsList.map(item =>
              <li key={item.product.id}>
                <div className="cart-image">
                  <img src={item.product.image} alt="product" />
                </div>
                <div className="cart-name">
                  <div>
                    <Link to={"/products/" + item.product.id}>
                      {item.product.name}
                    </Link>

                  </div>
                  <div>
                    Qty:{' '}
                    <select className="qty-select" value={item.productQuantity} onChange={(e) => dispatch(addToCart(item.product.id, e.target.value, "replaceQty"))}>
                        {[...Array(item.product.quantityInStock).keys()].map(x =>
                          <option key={x + 1} value={x + 1}>{x + 1}</option>
                        )}
                    </select>
                  </div>
                </div>
                <div className="cart-price">
                    Rp{item.productPrice}
                </div>
                <button type="button" className="cart-delete" onClick={() => removeFromCartHandler(item.product.id)} >
                    <FontAwesomeIcon icon={faTrash} size="lg" />
                </button>
              </li>
            )
        }
      </ul>

    </div>
    <div className="cart-action">
      <div className="items">
        Subtotal ( {totalProducts} items)
        :
      </div>
      <div className="price"> Rp {totalProductsPrice}</div>
      <button onClick={checkoutHandler} className="button primary full-width" disabled={cartItemsList.length===0}>
        Proceed to Checkout
      </button>

    </div>

  </div>
}

export default CartScreen;