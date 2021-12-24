import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { logout, update } from '../actions/userActions';
import { emptyCartAfterSignOut } from '../actions/cartActions';
import { useDispatch, useSelector } from 'react-redux';
import { GoogleLogout } from 'react-google-login';
import { CLIENT_ID } from '../constants/client';

function ProfileScreen(props) {
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [mobilePhone, setMobilePhone] = useState('');
  const [image, setImage] = useState('');

  const userSignin = useSelector(state => state.userSignin);
  const { userInfo } = userSignin;

  const dispatch = useDispatch();
  useEffect(() => {
    setName(userInfo?.name);
    setMobilePhone(userInfo?.mobilePhone);
    setImage(userInfo?.image);
  }, [userInfo]);


  const handleLogout = () => {
    dispatch(logout());
    dispatch(emptyCartAfterSignOut());
    props.history.push("/signin");
  }
  const submitHandler = (e) => {
    e.preventDefault();
    console.log("submit update prof")
    dispatch(update({ userId: userInfo.id, name, password, mobilePhone, image }))
  }
  const userUpdate = useSelector(state => state.userUpdate);
  const { loading, success, error } = userUpdate;

  if(!userInfo) return <Redirect to="/signin?redirect=" />

  return <div className="profile">
    <div className="profile-info">
      <div className="form">
        <form onSubmit={submitHandler} >
          <ul className="form-container">
            <li>
              <div className="user">
                <h2>User Profile</h2>
                {
                  image ?
                    <img src={image} alt="user-profile-img" className="user-profile-img" />
                  :
                    <img src='https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg' 
                    alt="default-img" className="user-profile-img"/>
                }
              </div>
            </li>
            <li>
              {loading && <div>Loading...</div>}
              {error && <div>{error}</div>}
              {success && <div>Profile Saved Successfully.</div>}
            </li>
            <li>
              <label htmlFor="name">
                Name
          </label>
              <input value={name} type="name" name="name" id="name" onChange={(e) => {
                if(userInfo?.type==="regular") setName(e.target.value)
              }}>
              </input>
            </li>
            <li>
              <label htmlFor="email">
                Username
            </label>
              <input readOnly value={userInfo?.username || ''} type="email" name="email" id="email" >
              </input>
            </li>
            <li>
              <label htmlFor="password">Password</label>
              <input required value={password} type="password" id="password" name="password" onChange={(e) => {
                if(userInfo?.type==="regular") setPassword(e.target.value)
                }}>
              </input>
            </li>
            <li>
              <label htmlFor="phone">
                Mobile Phone
              </label>
              <input type="text" value={mobilePhone} name="phone" id="phone" onChange={(e) => {
                if(userInfo?.type==="regular") setMobilePhone(e.target.value)
                }}>
              </input>
            </li>
            <li>
              <label htmlFor="image">
                Image
              </label>
              <input type="text" value={image} name="image" id="image" onChange={(e) => {
                if(userInfo?.type==="regular") setImage(e.target.value)
                }}>
              </input>
            </li>
            <li>
              <button disabled={userInfo?.type==="regular"?false:true} type="submit" className="button primary">Update</button>
            </li>
            <li>
              {
                userInfo?.type==='regular' ?
                <button type="button" onClick={handleLogout} className="button secondary full-width text-center">Logout</button>
                :
                <GoogleLogout
                  clientId={CLIENT_ID}
                  buttonText="Logout"
                  render={renderProps => (
                    <button className="google-btn" onClick={renderProps.onClick} disabled={renderProps.disabled}>
                      <div className="google-icon-wrapper">
                        <img alt="google" className="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
                      </div>
                      <p className="btn-text"><b>Sign Out</b></p>
                    </button>
                    )}
                  onLogoutSuccess={handleLogout}
                >
                </GoogleLogout>
              }
              </li>

          </ul>
        </form>
      </div>
    </div>
  </div>
}

export default ProfileScreen;