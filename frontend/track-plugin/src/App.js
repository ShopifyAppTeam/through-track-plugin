import logo from './logo.svg';
import './App.css';
import { useState } from 'react';
import api from './api/axiousConfig.js';
import axios from 'axios';

import Layout from './components/Layout';
import {Routes, Route} from 'react-router-dom';
import Home from   './components/home/Home';
import Header from './components/header/Header';
import NotFound from './components/notfound/NotFound';
import SignIn from './components/signin/SignIn';
import SignUp from './components/signup/SignUp';
import Orders from './components/orders/Orders';
import Settings from './components/settings/Settings';


function App() {

  return (
      <div className="App">
        <Header/>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="/" element={<Home />}></Route>
            <Route path="/orders" element={<Orders />}></Route>
            <Route path="/login" element={<SignIn />}></Route>
            <Route path="/register" element={<SignUp />}></Route>
            <Route path="/settings" element={<Settings/>}></Route>
            <Route path="*" element={<NotFound/>}></Route>

          </Route>
        </Routes>

      </div>
  );
    
  
}

export default App;
