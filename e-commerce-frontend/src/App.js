import React, { useEffect } from 'react';
import { BrowserRouter, Route, Link } from 'react-router-dom';
import './App.css';
import HomeScreen from './screens/HomeScreen';
import ProductScreen from './screens/ProductScreen';
import CartScreen from './screens/CartScreen';
import SigninScreen from './screens/SigninScreen';
import { useDispatch, useSelector } from 'react-redux';
import RegisterScreen from './screens/RegisterScreen';
import ProductsScreen from './screens/ProductsScreen';
import ProfileScreen from './screens/ProfileScreen';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart, faTimes } from '@fortawesome/free-solid-svg-icons';
import { getCart } from './actions/cartActions';
import { listProductCategory } from './actions/productCategoryActions';
import { getRoles } from './actions/roleActions';

function Header(props){
  const cart = useSelector(state => state.cart);
  const { cartItemsList } = cart;
  const {openMenu,userInfo} = props;
  return <header className="header">
    <div className="brand">
      <button onClick={openMenu}>&#9776;</button>
      <Link to="/">bmart</Link>
    </div>
    <div className="header-links">
      <Link className="cart-icon" to="/cart">
        {
          userInfo ?
          cartItemsList.length===0?null:
          <div>{cartItemsList.length}</div>
          :
          null
        }
        <FontAwesomeIcon icon={faShoppingCart} size="lg" />
      </Link>
      {userInfo ? (
        <Link to="/profile">{userInfo.name ? userInfo.name : userInfo.username}</Link>
      ) : (
        <Link to="/signin">Sign In</Link>
      )}
      {userInfo && userInfo.role === "admin" && (
        <div className="dropdown">
          <a href="#">Admin</a>
          <ul className="dropdown-content">
            <li>
              <Link to="/products">Products</Link>
            </li>
          </ul>
        </div>
      )}
    </div>
  </header>
}

function SideBar(props){
  const productCategory = useSelector(state => state.productCategory);
  const { productCategoryList } = productCategory;

  return <aside className="sidebar">
          <h3>Shopping Categories</h3>
          <button className="sidebar-close-button" onClick={props.closeMenu}>
            <FontAwesomeIcon icon={faTimes} size="lg" />
          </button>
          <ul className="categories">
            {
              productCategoryList.map((category) =>(
              <li key={category.id}>
                <Link to={"/category/"+category.name}>{category.name}</Link>
              </li>
              ))
            }
          </ul>
        </aside>
}

function App() {
  const userSignin = useSelector((state) => state.userSignin);
  const { userInfo } = userSignin;
  const dispatch = useDispatch();
  if(userInfo) dispatch(getCart());
  dispatch(listProductCategory());
  dispatch(getRoles());

  useEffect(() => {
   
  }, [userInfo]);
  

  const openMenu = () => {
    document.querySelector('.sidebar').classList.add('open');
  };
  const closeMenu = () => {
    document.querySelector('.sidebar').classList.remove('open');
  };
  
  return (
    <BrowserRouter>
      <div className="grid-container">
        <Header openMenu={openMenu} userInfo={userInfo} />
        <SideBar closeMenu={closeMenu} />
        <main className="main">
          <div className="content">
            <Route path="/profile" component={ProfileScreen} />
            <Route path="/products" component={ProductsScreen} />
            <Route path="/signin" component={SigninScreen} />
            <Route path="/register" component={RegisterScreen} />
            <Route path="/product/:id" component={ProductScreen} />
            <Route path="/cart/:id?" component={CartScreen} />
            <Route path="/category/:id" component={HomeScreen} />
            <Route path="/search" component={HomeScreen} />
            <Route path="/" exact={true} component={HomeScreen} />
          </div>
        </main>
        <footer className="footer">All right reserved.</footer>
      </div>
    </BrowserRouter>
  );
}

export default App;
