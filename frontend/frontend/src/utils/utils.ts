import {Gender} from '$lib/types';

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

function calculateAge(birthdate: Date) {
  const today = new Date();
  const age = today.getFullYear() - birthdate.getFullYear();

  // Adjust age if birthday hasn't occurred yet this year
  const birthMonth = birthdate.getMonth();
  const todayMonth = today.getMonth();
  if (todayMonth < birthMonth || (todayMonth === birthMonth && today.getDate() < birthdate.getDate())) {
    return age - 1;
  }

  return age;
}

function parseYYYYMMDDToDate(date: string): Date | null {
  const dateParts = date.split('-');
  if (dateParts.length === 3) {
    const year = parseInt(dateParts[0], 10);
    const month = parseInt(dateParts[1], 10);
    const day = parseInt(dateParts[2], 10);


    if (!isNaN(month) && !isNaN(day) && !isNaN(year)) {
      // Note: Months in JavaScript Date are 0-based, so we subtract 1 from the month
      return new Date(year, month - 1, day);
    }
  }

  // If the input date string is not in the expected format, return null or handle the error as needed.
  return null;
}


function formatDateToYYYYMMDD(date: Date) {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Add 1 because months are zero-based
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

function translateGender(gender: Gender) {
  if (gender === Gender.MALE) {
    return 'Masculino';
  } else if (gender === Gender.FEMALE) {
    return 'Feminino';
  } else {
    return 'Outro';
  }
}

function reverseTranslateGender(gender: string): Gender {
  if (gender.toLowerCase() === 'masculino') {
    return Gender.MALE;
  } else if (gender.toLowerCase() === 'feminino') {
    return Gender.FEMALE;
  } else {
    return Gender.OTHER;
  }
}

export {
  fetchWithAuth,
  getAuthorizationToken,
  formatDateToYYYYMMDD,
  formatDateToDDMMYYYY,
  parseYYYYMMDDToDate,
  calculateAge,
  translateGender,
  reverseTranslateGender
};