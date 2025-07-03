import React from 'react'
import './home.css'

function Home() {
  return (
    <div className='homepage d-flex flex-row'>
      {/* <div className='d-flex justify-content-center car-section'>
       <img className='img-fluid car-img' src="/car1.png" alt="" />
      </div> */}

      <div className='hero d-flex flex-row justify-content-evenly align-items-center'>
        <div className=''>
          <div className=''>
            <input className='from c-input' placeholder='Enter Pickup Location' type="text" name="" id="" />
            <img className='input-icon' src="/icons/map-pin.svg" alt="" />
          </div>
          <div className='inputcon'>
            <input className='c-input' placeholder='Enter Drop Location' type="text" />
            <img className='input-icon' src="/icons/stop-circle.svg" alt="" />
          </div>

          <label className='fw-bold' htmlFor="">Date and Time </label>
          <div className='inputcon-half'>
            <input className='d-inp' placeholder='' type="date" />
            <img className='input-icon' src="/icons/calendar.svg" alt="" />
          </div>
        </div>

        <div>image</div>
      </div>
      
    </div>
  )
}

export default Home