// This code is used to translate a given piece of text from its original
// language into a specified target language using Google Cloud's Translation API.

import axios from 'axios';

const API_KEY = 'AIzaSyAKEGtAocA3B9E_PiWR_PvwBxnuYy3_Rrk';

export async function translateText(text: string, targetLanguage: string): Promise<string> {
    const url = `https://translation.googleapis.com/language/translate/v2?key=${API_KEY}`;

    const response = await axios.post(url, {
        q: text,
        target: targetLanguage
    });

    return response.data.data.translations[0].translatedText;
}
