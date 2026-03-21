import './App.css';
import { createClient } from '@supabase/supabase-js';
import { useState } from "react";

const date = new Date().toString();
const anything = 80138019;
const SUPABASE_PROJECT_URL = "https://txoajgqjzwrwpljlsqpr.supabase.co";
const SUPABASE_PUBLISHABLE_KEY = "sb_publishable_RTB3ZUp3nwQhB26poC8GJQ_CEksRzUh";
const supabase = createClient(SUPABASE_PROJECT_URL, SUPABASE_PUBLISHABLE_KEY);


// let pojoArray = [
//   { id_number:"38", first_name:"Hazel", last_name:"Martinez" },
//   { id_number:"48", first_name:"Delilah", last_name:"Rodriguez" },
//   { id_number:"53", first_name:"Hugo", last_name:"Miller" }
// ];

function App() {
  return (
    <div className="App">
      <h1>Hello 4325364</h1>
      <p>{date} {anything}</p>
      <POJOList />.
      <hr />
    </div>
  );
}

function MyPOJO(properties) {
const [state, setState] = useState( {changed: false, pojo: properties.pojo});
const pojo = state.pojo;
const changed = state.changed;
  return (
    <div className="MyPOJO"
      style={{
        border: changed ? "solid red" : "solid white"
      }}
    >
      <input type="text" disabled defaultValue={pojo.id_number} />
      <input 
        type="text" 
        defaultValue={pojo.first_name} 
        onInput={
          (event)=>setState({
            changed:true,
            pojo:{id_number:pojo.id_number, first_name:event.target.value, last_name:pojo.last_name}
          })
        } 
      />
      <input 
        type="text" 
        defaultValue={pojo.last_name} 
        onInput={
          (event)=>setState({
            changed:true,
            pojo:{id_number:pojo.id_number, first_name:pojo.first_name, last_name:event.target.value}
          })
        }
      />
      <button
        type="button"
        disabled={!changed}
         onClick={
          async () => { 
            const { error } = await supabase.from('mypojo').update(pojo).eq("id_number",pojo.id_number);
            console.log(JSON.stringify(error));
            // clear the changed flag
            setState({changed:false,pojo:pojo});
          }
        }
      >Save</button>
      <button
        type="button"
        onClick={
          async () => { 
            const { error } = await supabase.from('mypojo').delete().eq("id_number",pojo.id_number);
            console.log(JSON.stringify(error));
            properties.refreshPOJOList();
          }
        }
      >Delete</button>
    </div>
  );
}

function POJOList() {
  const [pojoArray, setPojoArray] = useState(null);
  if (pojoArray == null) {
    supabase.from('mypojo').select().order('id_number').then(
      ({ data, error }) => {
        data.push({id_number:"NEW"});
        setPojoArray(data); }
    );
    return <div>Loading...</div>;
  } else {
    const listItems = pojoArray.map(item =>
      <div
        key={item.id_number}
      >
      <MyPOJO pojo={item} refreshPOJOList={setPojoArray} />
      </div>
    );
    return (
      <div className="POJOList">
        {listItems}
      </div>
    );
  }
}

// supabase.from('mypojo').select().then((result) => {
//   console.log(result.data);
//   console.log(result.error);
// }); 

export default App;
