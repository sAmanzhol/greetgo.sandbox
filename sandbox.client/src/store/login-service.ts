import axios from 'axios'
import {PersonDisplay} from "@/model/PersonDisplay";

export interface LoginService {
  loadPersonDisplay(): Promise<PersonDisplay>;

  login(username: string, password: string): Promise<string>;

  exit(): Promise<void>;
}

export const loginService = {

  async loadPersonDisplay(): Promise<PersonDisplay> {
    return axios.get('/auth/displayPerson').then(response => response.data as PersonDisplay);
  },

  async login(username: string, password: string): Promise<string> {

    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);

    return await axios.post('/auth/login', params).then(response => {
      return response.data as string;
    }).catch(error => {
      console.log("asd123 error = ", error);
      throw error.response.data;
    });
  },

  exit(): Promise<void> {
    return axios.get('/auth/exit').then(() => {});
  }

} as LoginService;
