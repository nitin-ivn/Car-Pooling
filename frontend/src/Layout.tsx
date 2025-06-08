import React from 'react'
import { Outlet } from 'react-router'
import NavBar from './Components/TopBar/NavBar'

function Layout() {
  return (
    <>
       <NavBar />
       <Outlet /> 
    </>
  )
}

export default Layout