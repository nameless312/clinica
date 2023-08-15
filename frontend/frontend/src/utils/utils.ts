async function fetchWithAuth(url: string, options: RequestInit = {}, token: string): Promise<Response> {
  options.headers = {
    ...options.headers,
    Authorization: `Bearer ${token}`
  };

  return await fetch(url, options);
}

function getAuthorizationToken(cookie: string): string | null {
  // Your logic to extract the token from the cookie
  const match = /Authorization=([^;]+)/.exec(cookie);
  if (match) {
    return match[1];
  } else {
    return null;
  }
}

function formatDateToDDMMYYYY(dateStr: string) {
  const date = new Date(dateStr);

  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are zero-indexed
  const year = date.getFullYear();

  return `${day}/${month}/${year}`;
}

function calculateAge(birthdate: string) {
  const birthDate = new Date(birthdate);

  const today = new Date();
  const age = today.getFullYear() - birthDate.getFullYear();

  // Adjust age if birthday hasn't occurred yet this year
  const birthMonth = birthDate.getMonth();
  const todayMonth = today.getMonth();
  if (todayMonth < birthMonth || (todayMonth === birthMonth && today.getDate() < birthDate.getDate())) {
    return age - 1;
  }

  return age;
}

function parseDDMMYYYYToDate(dateString) {
  const parts = dateString.split('/');
  if (parts.length === 3) {
    const day = parseInt(parts[0], 10);
    const month = parseInt(parts[1], 10) - 1; // Month is 0-indexed in JavaScript Date
    const year = parseInt(parts[2], 10);

    return new Date(year, month, day);
  } else {
    throw new Error('Invalid date format');
  }
}


export {fetchWithAuth, getAuthorizationToken, formatDateToDDMMYYYY, parseDDMMYYYYToDate, calculateAge};