import Axios from "axios";
import {
  GET_ROLE_LIST, GET_ROLE_LIST_FAIL
} from "../constants/roleConstants";
import { BASE_URL } from "../constants/api";

const getRoles = () => async (dispatch) => {
  try{
    const { data } = await Axios.get(BASE_URL+"/role");
    if(data.code!==200) throw {message:data.errors.error[0]};
    const role = data.data.content; 
    console.log("[GET] role list : ",role)
    dispatch({ 
      type: GET_ROLE_LIST, payload: role 
    });

  }catch (error){
    dispatch({ type: GET_ROLE_LIST_FAIL, payload: error.message });
  }
  
}


export { getRoles };