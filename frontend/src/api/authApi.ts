import api from "./axios";

import type {
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
  User,
} from "../types/auth";

export const register = async (
  data: RegisterRequest
): Promise<RegisterResponse> => {

  const response = await api.post<RegisterResponse>(
    "/auth/register",
    data
  );

  return response.data;
};

export const login = async (
  data: LoginRequest
): Promise<LoginResponse> => {

  const response = await api.post<LoginResponse>(
    "/auth/login",
    data
  );

  return response.data;
};

export const getUsername = async (): Promise<User> => {

  const response = await api.get<User>(
    "/auth/username"
  );

  return response.data;
};

export const refreshToken = async (
  token: string
): Promise<LoginResponse> => {

  const response = await api.post<LoginResponse>(
    "/auth/refresh",
    {
      refreshToken: token,
    }
  );

  return response.data;
};

export const logout = async (
  token: string
): Promise<void> => {

  await api.post("/auth/logout", {
    refreshToken: token,
  });
};