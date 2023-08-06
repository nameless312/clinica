import React from 'react';
import ReactDOM from "react-dom/client";
import './index.css';
import { ChakraProvider, createStandaloneToast } from '@chakra-ui/react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import AuthProvider from './context/AuthContext';
import Home from './Home';
import Login from './components/login/Login';


const { ToastContainer } = createStandaloneToast();

const router = createBrowserRouter([
	{
        path: "/",
        element: <Login/>
    },
	{
		path: "/dashboard",
		element : <Home/>
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


