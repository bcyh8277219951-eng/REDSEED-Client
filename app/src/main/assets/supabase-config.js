"use strict";

import { createClient } from "https://cdn.jsdelivr.net/npm/@supabase/supabase-js@2/+esm";

const SUPABASE_URL = "https://kfrzilzyvnaimgiirmaf.supabase.co";
const SUPABASE_PUBLISHABLE_KEY =
"sb_publishable_D-MMJTgfqNdPzDd8PgGfAA_p30M5_Jn";

const SUPABASE_ANON_KEY =
SUPABASE_PUBLISHABLE_KEY;

const SUPABASE_BUCKET = "RedSeedDB";
const SUPABASE_PROJECTS_TABLE = "projects";

const supabaseClient = createClient(
    SUPABASE_URL,
    SUPABASE_PUBLISHABLE_KEY,
    {
        auth: {
            persistSession: true,
            autoRefreshToken: true,
            detectSessionInUrl: true
        }
    }
);

const supabase = supabaseClient;

export {
    supabase,
    supabaseClient,
    SUPABASE_URL,
    SUPABASE_PUBLISHABLE_KEY,
    SUPABASE_ANON_KEY,
    SUPABASE_BUCKET,
    SUPABASE_PROJECTS_TABLE
};
