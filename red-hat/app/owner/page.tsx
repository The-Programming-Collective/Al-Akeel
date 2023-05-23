'use client';

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';
import { parseArgs } from 'util';


export default function Page() {
    const [username, setUsername] = useState<any>(localStorage.getItem('username')?.replaceAll('"', ''));
    const [password, setPassword] = useState<any>(localStorage.getItem('password')?.replaceAll('"', ''));
    const [itemNames, setItemNames] = useState<any>([]);
    const [itemName, setItemName] = useState<any>('');
    const [itemPrices, setItemPrices] = useState<any>([]);
    const [itemPrice, setItemPrice] = useState<any>('');
    const [restaurantId, setRestaurantId] = useState<any>('');
    const [restaurant, setRestaurant] = useState<any>('');
    const [deleteMealId, setDeletedMealId] = useState<any>('');
    const [mealId, setMealId] = useState<any>([]);
    // const auth = 'Basic ' + btoa(username + ':' + password);
    const auth = 'Basic aGVja2VyOmhlY2tlcg=='


    const handleGetRestaurant = (e: any) => {
        var url = 'http://localhost:8080/Al-Akeel/api/owner/restaurant?restaurant_id=' + restaurantId;
        e.preventDefault();
        // console.log(url);
        fetch(url, {
            method: 'GET',
            headers: {
                authorization: auth
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setRestaurant(data);
                console.log(data);
            });
    };

    // TODO: fix this
    const handleCreate = (e: any) => {
        e.preventDefault();
        var itemsNames = itemNames.split(',');
        var itemsPrices = itemPrices.split(',');
        const result = itemsNames.map((name: any, index: string | number) => ({ name, price: itemsPrices[index] }));
        const data = { value1:restaurantId, value2: result };
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/owner/menu', {
            method: 'POST',
            headers: {
                accept: '*/*',
                'Content-Type': 'application/json',
                authorization: auth
            },
            body: JSON.stringify(data),
            })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                handleGetRestaurant(e);
            }
            );
        }
        

    const handleAdd = (e: any) => {
        e.preventDefault();
        console.log(JSON.stringify(itemName));
        console.log(JSON.stringify(itemPrice));
        let data = { value1: parseInt(restaurantId, 10), value2: { name: itemName, price: parseInt(itemPrice, 10) } };
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/owner/meal', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                authorization: auth,
                accept: '*/*',
            },
            body: JSON.stringify(data),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                handleGetRestaurant(e);
            }
            );
    }


    const handleRemove = (e: any) => {
        e.preventDefault();
        let url = 'http://localhost:8080/Al-Akeel/api/owner/meal?retaurant_id='+restaurantId+'&meal_id='+deleteMealId;
        console.log(url);
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Methods": "GET, PUT, POST, DELETE",
                'Access-Control-Allow-Origin': '*',
                authorization: auth,
                accept: '*/*',
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                handleGetRestaurant(e);
            }
            ).catch((error) => {console.log(error);});
    }


    const handleReport = (e: any) => {

    }


    return (
        <main>
            <nav className={styles.center}>
                <Link href="/">
                    Al-Akeel | Home
                </Link>{' '}
            </nav>
            <div className={styles.main}>
                <h1 className={styles.h1}>Owner Account</h1>
                <br></br>
                <br></br>
                <h2 className={styles.h2}>Welcome {username}</h2>
                <br></br>
                <form className={styles.form}>
                    <h2>Restaurant ID: </h2>
                    <br></br>
                    <label className={styles.label}>Restaurant ID: </label>
                    <input className={styles.input} type="text" name="restaurantId" value={restaurantId} onChange={(e) => setRestaurantId(e.target.value)} />
                    <br></br>
                    <button className={styles.submit} onClick={handleGetRestaurant} type="submit">Submit</button>
                </form>
                <div>
                    <h2>Restaurant: </h2>
                    <br></br>
                    <h3>Restaurant ID: {restaurant.id}</h3>
                    <h3>Restaurant Name: {restaurant.name}</h3>
                    <ul className={styles.list}>
                        {restaurant.mealsList?.map((item: any) => (
                            <li key={item.id}>
                                <h3>Meal ID: {item.id}</h3>
                                <h3>Meal Name: {item.name}</h3>
                                <h3>Meal Price: {item.price} EGP</h3>
                            </li>
                        ))}
                    </ul>
                </div>
                <form className={styles.form}>
                    <h2>Create Menu: </h2>
                    <br></br>
                    <label className={styles.label}>Item Name: </label>
                    <input className={styles.input} type="text" name="itemName" onChange={(e) => setItemNames(e.target.value)}/>
                    <label className={styles.label}>Item Price: </label>
                    <input className={styles.input} type="text" name="itemPrice" onChange={(e) => setItemPrices(e.target.value)}/>
                    <br></br>
                    <button className={styles.submit} onClick={handleCreate} type="submit">Submit</button>
                </form>
                <form className={styles.form}>
                    <h2>Add meal: </h2>
                    <br></br>
                    <label className={styles.label}>Item Name: </label>
                    <input className={styles.input} type="text" name="itemName" value={itemName} onChange={(e) => setItemName(e.target.value)} />
                    <label className={styles.label}>Item Price: </label>
                    <input className={styles.input} type="text" name="itemPrice" value={itemPrice} onChange={(e) => setItemPrice(e.target.value)} />
                    <br></br>
                    <button className={styles.submit} onClick={handleAdd} type="submit">Submit</button>
                </form>
                <form className={styles.form}>
                    <h2>Remove meal: </h2>
                    <br></br>
                    <label className={styles.label}>Meal ID: </label>
                    <input className={styles.input} type="text" name="deleteMealId" value={deleteMealId} onChange={(e) => setDeletedMealId(e.target.value)} />
                    <br></br>
                    <button className={styles.submit} onClick={handleRemove} type="submit">Submit</button>
                </form>
            </div>
        </main>
    )
}
