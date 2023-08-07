import { ReactNode, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

interface ProtectedRouteProps {
    children: ReactNode;
  }

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {

    const { isUserAuthenticated } = useAuth()
    const navigate = useNavigate();

    const [redirected, setRedirected] = useState(false); // Track if already redirected

    useEffect(() => {
        // Check if user is not authenticated and not already redirected
        if (!isUserAuthenticated() && !redirected) {
        setRedirected(true); // Set the state to prevent further redirects
        navigate("/");
        }
    }, [isUserAuthenticated, navigate, redirected]);

    return isUserAuthenticated() ? <>{children}</> : null;
}

export default ProtectedRoute;