async function fetchWithAuth(url: string, options: RequestInit = {}): Promise<Response> {
  const token = getAuthorizationToken(); // Implement this function to retrieve the token from the cookie
  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`
  };

  return await fetch(url, options);
}

async function fetchWithAuthAndCookie(url: string, options: RequestInit = {}, token: string): Promise<Response> {
  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`
  };

  return await fetch(url, options);
}

function getAuthorizationToken(): string | null {
  // Implement this function to retrieve the token from the cookie
  const cookie = document.cookie;
  // Your logic to extract the token from the cookie
  const match = /Authorization=([^;]+)/.exec(cookie);
  if (match) {
    return match[1];
  } else {
    return null;
  }
}

export {fetchWithAuth, fetchWithAuthAndCookie};