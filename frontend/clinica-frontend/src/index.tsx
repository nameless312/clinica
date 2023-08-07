import React from 'react';
import ReactDOM from "react-dom/client";
import './index.css';
import { ChakraProvider, createStandaloneToast } from '@chakra-ui/react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import AuthProvider from './components/context/AuthContext';
import Home from './Home';
import Login from './components/login/Login';
import ProtectedRoute from './components/shared/ProtectedRoute';


const { ToastContainer } = createStandaloneToast();

const router = createBrowserRouter([
	{
        path: "/",
        element: <Login/>
    },
	{
		path: "/dashboard",
		element : <ProtectedRoute><Home/></ProtectedRoute>
	}
])

const root = ReactDOM.createRoot(
	document.getElementById('root') as HTMLElement
  );

root.render(
	<React.StrictMode>
		<ChakraProvider>
			<AuthProvider>
				<RouterProvider router={router} />
			</AuthProvider>
			<ToastContainer />
		</ChakraProvider>
	</React.StrictMode>
)


