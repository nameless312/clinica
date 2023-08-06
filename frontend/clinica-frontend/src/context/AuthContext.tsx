import { PropsWithChildren, createContext, useContext, useEffect, useState } from "react";
import { getUser, performLogin } from "../services/client";
import jwt_decode from "jwt-decode";
import jwtDecode from "jwt-decode";


interface Credentials {
    email: string;
    password: string;
  }

interface User {
    userId: number,
    firstName: string,
    lastName: string,
    email: string,
}

interface AuthProviderProps {
    children: React.ReactNode
  }

interface AuthContextType {
    user: User | null
    login: (credentials: Credentials) => Promise<void>
    logOut: () => void
    isUserAuthenticated: () => boolean
    setUserFromToken: () => Promise<void>
}
  
const AuthContext = createContext<AuthContextType>({
    user: null,
    login: async (credentials: Credentials) => {},
    logOut: () => {},
    isUserAuthenticated: () => false,
    setUserFromToken: async () => {}
});


const AuthProvider: React.FC<AuthProviderProps> = ({ children }: PropsWithChildren<{}>) => {

    const [user, setUser] = useState<User | null>(null)

    const setUserFromToken = async () => {
        let token = localStorage.getItem("token");
        if (token) {
            const decodedToken: any = jwtDecode(token);
            const userId: number = decodedToken.userid;
            const userRes = await getUser(userId);
            const userObj: User = userRes.data;
            setUser(userObj);
        }
    }
    useEffect(() => {
        setUserFromToken()
    }, [])


    const login = async (credentials: Credentials) => {
        try {
            const res = await performLogin(credentials);
            const token = res.headers['authorization'];
            localStorage.setItem("token", token);
            const decodedToken: any = jwt_decode(token);
            const userId: number = decodedToken.userid;
            const userRes = await getUser(userId);
            const userObj: User = userRes.data;
            setUser(userObj);
        } catch (err) {
        throw err;
        }
    }

    const logOut = () => {
        localStorage.removeItem("token")
        setUser(null)
    }

    const isUserAuthenticated = () => {
        const token = localStorage.getItem("token");
        if (!token) {
            return false;
        }
        const decodedToken: any = jwtDecode(token);
        const expiration = decodedToken.exp
        if (Date.now() > expiration * 1000) {
            logOut()
            return false;
        }
        return true;
    }

    return (
        <AuthContext.Provider value={{
            user,
            login,
            logOut,
            isUserAuthenticated,
            setUserFromToken
        }}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider

export const useAuth = () => useContext(AuthContext)