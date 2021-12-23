import Axios from "axios";
import Cookie from 'js-cookie';
import {
  USER_SIGNIN_REQUEST, USER_SIGNIN_SUCCESS,
  USER_SIGNIN_FAIL, USER_REGISTER_REQUEST,
  USER_REGISTER_SUCCESS, USER_REGISTER_FAIL, USER_LOGOUT, USER_UPDATE_REQUEST, USER_UPDATE_SUCCESS, USER_UPDATE_FAIL
} from "../constants/userConstants";
import { BASE_URL } from "../constants/api";

const update = ({ userId, name, username, password }) => async (dispatch, getState) => {
  const { userSignin: { userInfo } } = getState();

  dispatch({ type: USER_UPDATE_REQUEST, payload: { userId, name, username, password } });
  try {
    const { data } = await Axios.put(BASE_URL+"/auth",
      { name, username, password }, {
      headers: {
        Authorization: 'Bearer ' + userInfo.token
      }
    });
    const user = data.data;
    dispatch({ type: USER_UPDATE_SUCCESS, payload: user });
    Cookie.set('userInfo', JSON.stringify(user));
  } catch (error) {
    dispatch({ type: USER_UPDATE_FAIL, payload: error.message });
  }
}

const signin = (username, password) => async (dispatch) => {
  dispatch({ type: USER_SIGNIN_REQUEST, payload: { username, password } });
  try {
    const { data } = await Axios.post(BASE_URL+"/auth/signin", { username, password });
    const user = data.data;
    dispatch({ type: USER_SIGNIN_SUCCESS, payload: user });
    Cookie.set('userInfo', JSON.stringify(user));
  } catch (error) {
    dispatch({ type: USER_SIGNIN_FAIL, payload: error.message });
  }
}

const register = (name, username, password) => async (dispatch) => {
  dispatch({ type: USER_REGISTER_REQUEST, payload: { name, username, password } });
  try {
    const { data } = await Axios.post(BASE_URL+"/auth/register", { name, username, password });
    const user = data.data;
    dispatch({ type: USER_REGISTER_SUCCESS, payload: user });
    Cookie.set('userInfo', JSON.stringify(user));
  } catch (error) {
    dispatch({ type: USER_REGISTER_FAIL, payload: error.message });
  }
}

const logout = () => (dispatch) => {
  Cookie.remove("userInfo");
  dispatch({ type: USER_LOGOUT })
}
export { signin, register, logout, update };