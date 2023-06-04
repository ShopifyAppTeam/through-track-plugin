import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTruckRampBox } from "@fortawesome/free-solid-svg-icons";
import Button from '@mui/material/Button';
import Container from "react-bootstrap/Container"
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import {Link, NavLink, Redirect} from "react-router-dom";

const Header = () => {
  return (
    <Navbar bg="light" variant="light" expand="lg">
        <Container fluid>
            <Navbar.Brand href="/" style={{"color" : "bg-light"}}>
                <FontAwesomeIcon icon ={faTruckRampBox} color="lime"/> <strong>TrackPlugin</strong>
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="navbarScroll" />
            <Navbar.Collapse id="navbarScroll">
                <Nav
                    className="me-auto my-2 my-lg-0"
                    style={{maxHeight: '100px'}}
                    navbarScroll
                >
                    <NavLink className ="nav-link" to="/">Home</NavLink> 
                    <NavLink className="nav-link" to="/orders">Orders</NavLink>

                </Nav>
                
                    
                <Button variant="outlined" component={Link} to="/login">Login</Button>
        
                <Button variant="contained" component={Link} to="/register">Register</Button>
            </Navbar.Collapse>
        </Container>
    </Navbar>
  );
}

export default Header