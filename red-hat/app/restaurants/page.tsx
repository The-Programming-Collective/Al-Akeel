'use client';

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [username, setUsername] = useState<any>(sessionStorage.getItem('username')?.replaceAll('"', ''));
    const [password, setPassword] = useState<any>(sessionStorage.getItem('password')?.replaceAll('"', ''));
    const [restaurant, setRestaurant] = useState<any>([]);
    const [mealId, setMealId] = useState<any>();
    const [restaurantId, setRestaurantId] = useState<any>();
    const [CCN, setCCN] = useState<any>();
    const [DATE, setDATE] = useState<any>();
    const [CVV, setCVV] = useState<any>();
    const [orderId, setOrderId] = useState<any>('');
    const auth = 'Basic ' + btoa(username + ':' + password);

    useEffect(() => {
        fetch('http://localhost:8080/Al-Akeel/api/customer/restaurants', {
            method: 'GET',
            headers: {
                accept: '*/*',
                'Content-Type': 'application/json',
                authorization: auth
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setRestaurant(data);
            });
    }, []);


    const handleSubmit = (e: any) => {
        e.preventDefault();
        var mealIds = mealId.split(',').map(function (item: any) {
            return parseInt(item, 10);
        });
        let value1 = restaurantId;
        let value2 = mealIds;
        const data = { value1: { CCN, DATE, CVV }, value2: { value1, value2 } };
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/customer/order', {
            method: 'POST',
            headers: {
                accept: '*/*',
                authorization: auth
            },
            body: JSON.stringify(data),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                alert("Order Added Successfully paid: " + data.totalPrice + " EGP\nOrder ID: " + data.id);
            }
            );
    }

    const handleChange = (e: any) => {
        e.preventDefault();
        var mealIds = mealId.split(',').map(function (item: any) {
            return parseInt(item, 10);
        });
        var value1 = orderId;
        var value2 = mealIds;
        const data = {value1 , value2};
        console.log(data);
        fetch('http://localhost:8080/Al-Akeel/api/customer/order', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                authorization: auth,
            },body: JSON.stringify(data),

        }).then((response) => response.json())
        .then((data) => {
            console.log(data);
            alert("Order Updated Successfully");
        }
        );

    }

    return (
        <main>
            <nav className={styles.center}>
                <Link href="/">
                    Al-Akeel | Home
                </Link>{' '}
            </nav>
            <div className={styles.main}>
                <h1 className={styles.h1}>Welcome: {username}</h1>
                <h1 className={styles.h1}>Restaurants</h1>
                <br></br>
                <div className={styles.grid}>
                    {restaurant.map((restaurant: any) => (
                        <a
                            className={styles.card}
                            target="_self"
                            rel="noopener noreferrer"
                        >
                            <h2>ID:{restaurant.id} - {restaurant.name} <span></span></h2>
                            <br></br>
                            <h3>Meals:</h3>
                            <br></br>
                            <ul>
                                {restaurant.mealsList.map((meal: any) => (
                                    <li key={meal.id}>ID:{meal.id} - {meal.name} ({meal.price})EGP</li>
                                ))}
                            </ul>
                        </a>
                    ))}
                </div>
            </div>
            <div className={styles.div}>
                <form onSubmit={handleSubmit} className={styles.form} action="/restaurants" method="post">
                    <h2> Make Order:</h2>
                    <label className={styles.label} htmlFor="restaurantId">Restaurant ID:</label>
                    <input className={styles.input} type="text" id="restaurantId" name="restaurantId" value={restaurantId} onChange={(e) => setRestaurantId(e.target.value)} />
                    <label className={styles.label} htmlFor="mealId">Meal ID:</label>
                    <input className={styles.input} type="text" id="mealId" name="mealId" value={mealId} onChange={(e) => setMealId(e.target.value)} />
                    <label className={styles.label} htmlFor="CCN">Credit Card Number:</label>
                    <input className={styles.input} type="text" id="CCN" name="CCN" value={CCN} onChange={(e) => setCCN(e.target.value)} />
                    <label className={styles.label} htmlFor="DATE">Date:</label>
                    <input className={styles.input} type="text" id="DATE" name="DATE" value={DATE} onChange={(e) => setDATE(e.target.value)} />
                    <label className={styles.label} htmlFor="CVV">CVV:</label>
                    <input className={styles.input} type="text" id="CVV" name="CVV" value={CVV} onChange={(e) => setCVV(e.target.value)} />
                    <button className={styles.submit} type="submit">Confirm Order</button>
                </form>
                <form onSubmit={handleChange} className={styles.form} action="/restaurants" method="post">
                    <h2> Edit Order:</h2>
                    <label className={styles.label} htmlFor="orderId">Order ID:</label>
                    <input className={styles.input} type="text" id="orderId" name="orderId" value={orderId} onChange={(e) => setOrderId(e.target.value)} />
                    <label className={styles.label} htmlFor="mealId">New Meal IDs:</label>
                    <input className={styles.input} type="text" id="mealId" name="mealId" value={mealId} onChange={(e) => setMealId(e.target.value)} />
                    <button className={styles.submit} type="submit">Confirm Change</button>
                </form>
            </div>
        </main>
    )
}
