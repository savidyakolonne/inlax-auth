import {
  createContext,
  useContext,
  useState,
  type ReactNode,
} from "react";

import {
  login as loginApi,
  logout as logoutApi,
  getUsername,
} from "../api/authApi";

import type {
  LoginRequest,
  User,
} from "../types/auth";

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (data: LoginRequest) => Promise<void>;
  logout: () => Promise<void>;
}

interface AuthProviderProps {
  children: ReactNode;
}

const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

export function AuthProvider({
  children,
}: AuthProviderProps) {

  const [user, setUser] = useState<User | null>(null);

  const isAuthenticated = user !== null;

  const login = async (
    data: LoginRequest
  ): Promise<void> => {

    const response = await loginApi(data);

    localStorage.setItem(
      "accessToken",
      response.accessToken
    );

    localStorage.setItem(
      "refreshToken",
      response.refreshToken
    );

    const username = await getUsername();

    setUser(username);
  };

  const logout = async (): Promise<void> => {

    const refreshToken =
      localStorage.getItem("refreshToken");

    const accessToken =
      localStorage.getItem("accessToken");

    if (refreshToken && accessToken) {
      await logoutApi(refreshToken);
    }

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth(): AuthContextType {

    const context = useContext(AuthContext);

    if (!context){
        throw new Error(
            "useAuth must be used inside AuthProvider"
        );
    }

    return context;
}