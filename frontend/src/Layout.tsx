import React from 'react'
import { Outlet } from 'react-router'
import NavBar from './Components/TopBar/NavBar'
import Footer from './Components/Footer/Footer'

function Layout() {
  return (
    <>
       <NavBar />
       <Outlet /> 
       <Footer />
    </>
  )
}

export default Layout