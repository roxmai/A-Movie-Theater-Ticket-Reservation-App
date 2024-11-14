import axois from 'axios';

const API_URL = 'http://localhost:8080/api/';

export async function getMovies(movie) {
    const response = await axois.get(API_URL + 'movies');
    return response.data;
}