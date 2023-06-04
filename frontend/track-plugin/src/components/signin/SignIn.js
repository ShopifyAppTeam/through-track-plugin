import Button from "react-bootstrap/Button";
import React from 'react'
import { useState } from 'react';
import { FormLabel } from 'react-bootstrap';
import Form from "react-bootstrap/Form";
import "./SignIn.css";

function SignIn() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const errors = {
        uname: "invalid username",
        pass: "invalid password"
    };

    const submit = (event) => {
        event.preventDefault();
    }

    const validate = () => {
        return email.length > 0 && password.length > 0;
    }


    return (
        <div className='SignIn'>
            <Form onSubmit={submit}>
                <Form.Group size="lg" controlId='email'>
                    <FormLabel>Email</FormLabel>
                    <Form.Control
                        autoFocus
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId='password'>
                    <FormLabel>Password</FormLabel>
                    <Form.Control
                        autoFocus
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />    
                </Form.Group>
                <Button block="false" size="lg" type="submit" disabled={!validate()}>
                    Login
                </Button>
                <div>Login with</div>
            </Form>
        </div>
    )
}

export default SignIn