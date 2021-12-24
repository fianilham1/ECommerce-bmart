import {
  GET_ROLE_LIST, GET_ROLE_LIST_FAIL
} from "../constants/roleConstants";

function roleListReducer(state = { roleList:[] }, action) {
  switch (action.type) {
    case GET_ROLE_LIST:
      return { roleList:action.payload };
    case GET_ROLE_LIST_FAIL:
      return { error: action.payload };
    default: return state;
  }
}

export {
  roleListReducer
}