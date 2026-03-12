console.log("here I am");

const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

// use port specified in environment variable or 6868 if none present
const PORT = process.env.PORT || 6868;

// start server (stop with red button in VSCode)
app.listen(PORT, () => {
    console.log("Server Listening on PORT:", PORT);
  });

  // serve files in public folder
app.use(express.static('public'));

app.get("/", (request, response) => {
    response.send("hello world!"); // a plain string return
});

app.get("/helloagain", (request, response) => {
    response.send([ "hello world!", "again", "hang on what's this?", "lying", "plank", "emu" ]);
});

const sb = require('@supabase/supabase-js');

const SUPABASE_PROJECT_URL = "https://txoajgqjzwrwpljlsqpr.supabase.co";
const SUPABASE_PUBLISHABLE_KEY = "sb_publishable_RTB3ZUp3nwQhB26poC8GJQ_CEksRzUh";

const supabase = sb.createClient(SUPABASE_PROJECT_URL, SUPABASE_PUBLISHABLE_KEY);

app.get("/pojo", async (request, response) => {
    const {data, error } = await supabase.from('mypojo').select();
    console.log(data);
    console.log(error);
    response.send(data);
});

app.get("/pojo/:id", async (request, response) => {
    // request.params will have fields for :fragments of URI
    const {data, error } = await supabase.from('mypojo').select().eq("id_number",request.params.id);
    if (data != null && data.length == 1) {
        response.send(data[0]);
    } else {
        response.status(404);
        response.send({}); // send an empty JSON object
    }
});

app.post("/pojo", async (request, response) => {
    const { data, error } = await supabase.from('mypojo').insert(request.body).select();
    console.log(error);
    if ( error != null) {
        response.send(error);
    } else {
        response.send(data);
    }
});

app.put("/pojo", async (request, response) => {
    const { data, error } = await supabase.from('mypojo').update(request.body).eq('id_number', request.body.id_number).select();
    console.log(error);
    if ( error != null) {
        response.send(error);
    } else {
        response.send(data);
    }
});

app.delete("/pojo/:id", async (request, response) => {
    // note the select returns the deleted item if any, so we can see if there was one or not
    const { data, error } = await supabase.from('mypojo').delete().eq("id_number",request.params.id).select();
    console.log(data);
    if ( error != null) {
        response.send(error);
    } else if (data.length == 1) {
        response.send(["item deleted"]);
    } else {
        response.status(404);
        response.send({}); // send an empty JSON object
    }
});
