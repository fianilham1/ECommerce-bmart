import Axios from "axios";
import Cookie from 'js-cookie';
import {
  USER_SIGNIN_REQUEST, USER_SIGNIN_SUCCESS, USER_SIGNIN_FAIL, 
  USER_REGISTER_REQUEST, USER_REGISTER_SUCCESS, USER_REGISTER_FAIL, 
  USER_LOGOUT, USER_UPDATE_REQUEST, USER_UPDATE_SUCCESS, USER_UPDATE_FAIL
} from "../constants/userConstants";
import { BASE_URL } from "../constants/api";
import { ShowSessionOut } from "../components/SessionAlert";
import { ShowRegisterSucces, ShowUpdateSucces } from "../components/Alert";

const update = ({ userId, name, password, mobilePhone, image }) => async (dispatch, getState) => {
  const { userSignin: { userInfo } } = getState();

  dispatch({ type: USER_UPDATE_REQUEST, payload: { userId, name, password, mobilePhone, image } });
  try {
    const { data } = await Axios.put(BASE_URL+"/auth",
      { name, password, mobilePhone, image }, {
      headers: {
        Authorization: 'Bearer ' + userInfo.token
      }
    });
     if(data.code!==200) throw {message:data.errors.error[0]};
    const user = data.data;
    dispatch({ type: USER_UPDATE_SUCCESS, payload: user });
    Cookie.set('userInfo', JSON.stringify(user));
    ShowUpdateSucces();
  } catch (error) {
    if(error.response.status===401) {
      ShowSessionOut()
      Cookie.remove("userInfo");
      dispatch({ type: USER_LOGOUT })
    };
    dispatch({ type: USER_UPDATE_FAIL, payload: error.message });
  }
}

const signin = (username, password) => async (dispatch) => {
  dispatch({ type: USER_SIGNIN_REQUEST, payload: { username, password } });
  try {
    const { data } = await Axios.post(BASE_URL+"/auth/signin", { username, password });
    if(data.code!==200) throw {message:data.errors.error[0]};
    const user = data.data;
    dispatch({ type: USER_SIGNIN_SUCCESS, payload: {
      ...user,
      type:"regular"
    } });
    Cookie.set('userInfo', JSON.stringify({
      ...user,
      type:"regular"
    }));
  } catch (error) {
    dispatch({ type: USER_SIGNIN_FAIL, payload: error.message });
  }
}

const register = (name, username, password, roleId, mobilePhone, image ) => async (dispatch) => {
  dispatch({ type: USER_REGISTER_REQUEST, payload: { name, username, password, roleId, mobilePhone, image } });
  try {
    const { data } = await Axios.post(BASE_URL+"/auth/register", { name, username, password, roleId, mobilePhone, image });
    if(data.code!==200) throw {message:data.errors.error[0]};
    const user = data.data;
    dispatch({ type: USER_REGISTER_SUCCESS, payload: user });
    Cookie.set('userInfo', JSON.stringify(user));
    ShowRegisterSucces();
  } catch (error) {
    dispatch({ type: USER_REGISTER_FAIL, payload: error.message });
  }
}

/*google*/
const googlesignin = (tokenId) => async (dispatch) => {
  dispatch({ type: USER_SIGNIN_REQUEST, payload: { tokenId } });
  try {
    const { data } = await Axios.post(BASE_URL+"/auth/google/signin", { tokenId });
     if(data.code!==200) throw {message:data.errors.error[0]};
    const user = data.data;
    dispatch({ type: USER_SIGNIN_SUCCESS, payload: {
      ...user,
      type:"google"
    } });
    Cookie.set('userInfo', JSON.stringify({
      ...user,
      type:"google"
    }));
  } catch (error) {
    console.log("error google")
    dispatch({ type: USER_SIGNIN_FAIL, payload: error.message });
  }
}

const logout = () => (dispatch) => {
  Cookie.remove("userInfo");
  dispatch({ type: USER_LOGOUT })
}

export { signin, register, logout, update, googlesignin };