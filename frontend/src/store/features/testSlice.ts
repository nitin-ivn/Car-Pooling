import { createSlice, type Slice } from "@reduxjs/toolkit";

const initialState = {
    marks : '20'
}

export const testSlice: Slice = createSlice({
    name: 'test',
    initialState,
    reducers: {},
})

export default testSlice.reducer;