import { Alert, AlertIcon, Box, Button, FormLabel, Heading, Input, Stack } from '@chakra-ui/react';
import { useAuth } from '../../context/AuthContext';
import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup'
import { useNavigate } from 'react-router-dom';
import { errorNotification } from '../../services/notification';
import { useEffect, useRef } from 'react';

interface MyTextInputProps {
	label: string
	name: string;
  	type: string;
  	id?: string;
  	placeholder?: string;
  }

const MyTextInput: React.FC<MyTextInputProps> = ({label, ...props}) => {
	const [field, meta] = useField(props)
	return (
		<Box>
			<FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
			<Input className="text-input" {...field} {...props}/>
			{meta.touched && meta.error ? (
				<Alert className="error" status={"error"} mt={2}>
					<AlertIcon/>
					{meta.error}
				</Alert>
			) : null}
		</Box>
	)
}

const LoginForm = () => {
	const { login } = useAuth();
	const navigate = useNavigate();
  	return (
		<Formik
		validateOnMount={true}
		validationSchema={
			Yup.object({
				email: Yup.string()
					.email("Email tem de ser válido")
					.required("Email é obrigatório"),
				password: Yup.string()
					.max(30, "Password não pode exceder 30 caracteres")
					.required("Password é obrigatória")
			})}
		initialValues={{email:"", password:""}}
		onSubmit={(values, {setSubmitting}) => {
			setSubmitting(true);
			login(values).then(() => {
				navigate("/")
			}).catch(err => {
				errorNotification(
					err.code,
					err.response.data.message
				)
			}).finally(() => {
				setSubmitting(false);
			})
		}}>
		{({isValid, isSubmitting}) => {
			return <Form>
				<Stack spacing={15}>
				<MyTextInput
					label={"Email"}
					name={"email"}
					type={"email"}
					placeholder={"example@gmail.com"}
				/>
				<MyTextInput
					label={"Password"}
					name={"password"}
					type={"password"}
					placeholder={"Inserir password"}
				/>

				<Button
					type={"submit"}
					disabled={!isValid || isSubmitting}>
					Login
				</Button>
				</Stack>
			</Form>
		}}

		</Formik>
	)
}

const Login = () => {
	const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            navigate("/dashboard");
        }
    });
	return (
		<>
		<div>
			<Heading>Login</Heading>
			<LoginForm/>
		</div>
		</>
	)
}

export default Login