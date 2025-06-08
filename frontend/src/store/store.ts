import { configureStore, type Store } from "@reduxjs/toolkit";
import testReducer from "./features/testSlice";

const store: Store = configureStore({
    reducer: {test: testReducer},
})

export type StoreType = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export default store;
