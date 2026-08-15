export interface RegisterRequest{
    fullName: string;
    email: string;
    password: string;
}

export interface RegisterResponse{
    id: number;
    fullName: string;
    username: string;
    email: string
}

export interface LoginRequest{
    email: string;
    password: string;
}

export interface LoginResponse{
    accessToken: string;
    refreshToken: string;
    tokenType: string;
}

export interface User {
    username: string;
}