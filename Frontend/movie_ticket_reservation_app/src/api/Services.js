import axois from 'axios';

const DEFAULT_PAGE_SIZE = 10;

const api = axios.create({
    baseURL: "http://localhost:8080", // Replace with your base API URL
    headers: {
      'Content-Type': 'application/json',
    },
  });

export const getMovies = async (page=1, pageSize=DEFAULT_PAGE_SIZE) => {
    const query = page == 1 ? "" : `?page=${page}&pageSize=${pageSize}`;
    const response = await api.get('/movies'+query);
    console.log(response.data);
    return response.data;
}

export const getGenres = async () => {
  const response = await api.get('/genres');
  return response.data;
}

export const getMoviesByGenre = async (genreId, page=1, pageSize=DEFAULT_PAGE_SIZE) => {
  const query = page == 1 ? "" : `?page=${page}&pageSize=${pageSize}`;
  const response = await api.get(`/movies/genre/${genreId}`+query);
  return response.data;
}

export const getMoviesBySearch = async (search, page=1, pageSize=DEFAULT_PAGE_SIZE) => {
  const query = page == 1 ? `?q=${search}` : `?q=${search}&page=${page}&pageSize=${pageSize}`;
  const response = await api.get(`/movies/search`+query);
  return response.data;
}