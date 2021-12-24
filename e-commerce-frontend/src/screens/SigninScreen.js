import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { signin, googlesignin } from '../actions/userActions';
import { GoogleLogin } from 'react-google-login';
import { CLIENT_ID } from '../constants/client';


function SigninScreen(props) {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const userSignin = useSelector(state => state.userSignin);
  const { loading, userInfo, error } = userSignin;
  const dispatch = useDispatch();
  const redirect = props.location.search ? props.location.search.split("=")[1] : '/';
  useEffect(() => {
    if (userInfo) {
      props.history.push(redirect);
    }
    return () => {
      //
    };
  }, [userInfo]);

  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(signin(username, password));
  }
  const responseGoogleSuccess = (response) => {
    console.log("success token ",response.tokenId);
    dispatch(googlesignin(response.tokenId));
  }
  const responseGoogleFailure = (response) => {
    console.log("failed ",response);
  }
  return <div className="form">
    <form onSubmit={submitHandler} >
      <ul className="form-container">
        <li>
          <h2>Sign-In</h2>
        </li>
        <li>
          {loading && <div>Loading...</div>}
          {error && <div>{error}</div>}
        </li>
        <li>
          <label htmlFor="email">
            Username
          </label>
          <input type="email" name="email" id="email" onChange={(e) => setUsername(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="password">Password</label>
          <input type="password" id="password" name="password" onChange={(e) => setPassword(e.target.value)}>
          </input>
        </li>
        <li>
          <button type="submit" className="button primary">Sign In</button>
        </li>
        <li>
          New to bmart?
        </li>
        <li>
          <Link to={redirect === "/" ? "register" : "register?redirect=" + redirect} className="button secondary text-center" >Create your bmart account</Link>
        </li>
        <li>
        <GoogleLogin
          clientId={CLIENT_ID}
          render={renderProps => (
          <button className="google-btn" onClick={renderProps.onClick} disabled={renderProps.disabled}>
            <div className="google-icon-wrapper">
              <img alt="google" className="google-icon" src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg"/>
            </div>
            <p className="btn-text"><b>Sign in with google</b></p>
          </button>
          )}
          onSuccess={responseGoogleSuccess}
          onFailure={responseGoogleFailure}
          cookiePolicy={'single_host_origin'}
        />
        </li>
      </ul>
    </form>
  </div>
}
export default SigninScreen;