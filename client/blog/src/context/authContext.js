import axios from "axios";
import { createContext, useEffect, useState } from "react";
export const AuthContext = createContext()

export const AuthContextProvider = ({ children }) => {
    const [currentUser, setcurrentUser] = useState(JSON.parse(localStorage.getItem("user")) || null)
   
    const login = async (inputs) => {

        const res = await axios.post("auth/signin", inputs)
        setcurrentUser(res.data)

    }
    const logout = () => {
        setcurrentUser(null)
    }
    useEffect(() => {
        localStorage.setItem("user", JSON.stringify(currentUser));
        
    }, [currentUser]);

    return (
        <AuthContext.Provider value={{ currentUser, login, logout }}>{children}</AuthContext.Provider>
    )

}