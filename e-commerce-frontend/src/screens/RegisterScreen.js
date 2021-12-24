import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { register } from '../actions/userActions';
import Swal from 'sweetalert2';

function RegisterScreen(props) {

  const [name, setName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [rePassword, setRePassword] = useState('');
  const [mobilePhone, setMobilePhone] = useState('');
  const [image, setImage] = useState('');

  const userRegister = useSelector(state => state.userRegister);
  const { loading, userInfo, error } = userRegister;
  const role = useSelector(state => state.role);
  const { roleList } = role;

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
    if(password===rePassword) return dispatch(register(name, username, password, roleList[1].id, mobilePhone, image));
    return Swal.fire({
      icon: 'error',
      title: 'Password is not match',
      showConfirmButton: false,
      timer: 1500
    })
  }
  return <div className="form">
    <form onSubmit={submitHandler} >
      <ul className="form-container">
        <li>
          <h2>Create Account</h2>
        </li>
        <li>
          {loading && <div>Loading...</div>}
          {error && <div>{error}</div>}
        </li>
        <li>
          <label htmlFor="name">
            Name
          </label>
          <input type="name" name="name" id="name" onChange={(e) => setName(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="email">
            Username
          </label>
          <input required type="email" name="email" id="email" onChange={(e) => setUsername(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="password">Password</label>
          <input required type="password" id="password" name="password" onChange={(e) => setPassword(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="rePassword">Re-Enter Password</label>
          <input required type="password" id="rePassword" name="rePassword" onChange={(e) => setRePassword(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="email">
            Role
          </label>
          <input value={roleList[1].name} type="text" name="role" id="role" >
          </input>
        </li>
        <li>
          <label htmlFor="phone">
            Mobile Phone
          </label>
          <input type="text" name="phone" id="phone"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*?)\..*/g, '$1');" 
          onChange={(e) => setMobilePhone(e.target.value)}>
          </input>
        </li>
        <li>
          <label htmlFor="image">
            Image
          </label>
          <input type="text" name="image" id="image" onChange={(e) => setImage(e.target.value)}>
          </input>
        </li>
        <li>
          <button type="submit" className="button primary">Register</button>
        </li>
        <li>
          Already have an account?
          <Link to={redirect === "/" ? "signin" : "signin?redirect=" + redirect} className="button secondary text-center" >Sign in your bmart account</Link>

        </li>

      </ul>
    </form>
  </div>
}
export default RegisterScreen;