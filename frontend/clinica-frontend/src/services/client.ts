import axios, { AxiosResponse } from "axios";

const getAuthConfig = () => ({
    headers: {
        Authorization: `${localStorage.getItem("token")}`
    }
})

export const getClients = async() => {
    try {
        return await axios.get(
            `${process.env.REACT_APP_API_BASE_URL}/client`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getUser = async(id: number): Promise<AxiosResponse>=> {
    try {
        return await axios.get(
            `${process.env.REACT_APP_API_BASE_URL}/user/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

interface Credentials {email: string, password: string}

export const performLogin = async(credentials: Credentials): Promise<AxiosResponse> => {
    try {
        return await axios.post(`${process.env.REACT_APP_API_BASE_URL}/auth/login`, credentials)
    } catch (e) {
        throw e;
    }
}
